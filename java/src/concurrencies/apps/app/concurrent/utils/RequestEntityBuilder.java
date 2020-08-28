package app.concurrent.utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RequestEntityBuilder {
    private MultipartBody.Builder multiPartFormBuilder = new MultipartBody.Builder();
    private SinglePartForm singlePartFormBuilder = new SinglePartForm();
    private RequestBodyType contentType = RequestBodyType.FORM_URLENCODED; //default body mime-type;

    //used for both single/multi part forms
    private List<String> names = new ArrayList<>();
    private List<String> values = new ArrayList<>();

    public enum RequestBodyType {
        /**
         * "CUSTOM" should not be used when calling {@code setBodyMimeType(PostBodyType mimeType)} except with {@code setBodyMimeType(String mimeType, boolean isMultiPart)}
         */
        MIXED(MultipartBody.MIXED, true), ALTERNATIVE(MultipartBody.ALTERNATIVE,
                true),
        DIGEST(MultipartBody.DIGEST, true), PARALLEL(MultipartBody.PARALLEL, true),
        FORM(MultipartBody.FORM, true), FORM_URLENCODED(
                MediaType.parse("application/x-www-form-urlencoded"), false),
        TEXT_PLAIN(MediaType.parse("text/plain"), false), JSON(
                MediaType.parse("application/json"), false),
        XML(MediaType.parse("application/xml"), false), CUSTOM(null, false);

        private MediaType value;
        private boolean isMultiPart;

        RequestBodyType(MediaType mediaType, boolean isMultiPart) {
            this.value = mediaType;
            this.isMultiPart = isMultiPart;
        }
    }

    /**
     * This method is normally not required except the cases where we need to specifically set multipart "Sub-type"
     * Ref: https://en.wikipedia.org/wiki/MIME#Multipart_subtypes
     *
     * @param mimeType
     * @return MultipartEntityBuilder
     */
    public RequestEntityBuilder setBodyMimeType(RequestBodyType mimeType) {
        contentType = mimeType;

        return this;
    }

    /**
     * Mime types, i.e. "application/json", "text/plain"
     *
     * @param mimeType
     * @param isMultiPart
     * @return
     */
    public RequestEntityBuilder setBodyMimeType(String mimeType,
                                                boolean isMultiPart) {
        contentType = RequestBodyType.CUSTOM;
        contentType.isMultiPart = isMultiPart;
        contentType.value = MediaType.parse(mimeType);

        return this;
    }

    /**
     * Use-case: For building with only single bodied raw string data, i.e: JSON raw data
     *
     * @param rawContent
     * @return
     */
    public RequestEntityBuilder buildFromRawData(String rawContent) {
        return singlePartFormBuilder.buildFromRawData(rawContent);
    }

    /**
     * Use-case: For building with only single bodied raw/binary file data, i.e: file binary/raw data
     *
     * @param rawContent
     * @return
     * @throws IOException
     */
    public RequestEntityBuilder buildFromBinaryData(File rawContent)
            throws IOException {
        return singlePartFormBuilder.buildFromBinaryData(rawContent);
    }

    public RequestEntityBuilder addParams(HashMap<String, String> paramsMap) {
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            names.add(entry.getKey());
            values.add(entry.getValue());
        }

        return this;
    }

    public RequestEntityBuilder addParam(String name, String value) {
        names.add(name);
        values.add(value);

        return this;
    }

    public RequestEntityBuilder addParam(String name, File file)
            throws IOException {
        return addParam(name, file, null, getFileMimeType(file));
    }

    public RequestEntityBuilder addParam(String name, File file, String fileName,
                                         String fileMimeType) {
        //Automatically set form data to multipart_form if it's not already set to a multipart option
        if (!contentType.isMultiPart) {
            setBodyMimeType(RequestBodyType.FORM);
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(fileMimeType),
                file);
        multiPartFormBuilder.addFormDataPart(name, fileName, requestBody);

        return this;
    }

    public String getFileMimeType(File file) throws IOException {
        return Files.probeContentType(file.toPath());
    }

    public BufferedSinkProgressHandler addStreamingData(String uploadName,
                                                        File file) throws IOException {
        return addStreamingData(uploadName, Files.readAllBytes(file.toPath()),
                getFileMimeType(file));
    }

    /**
     * Check a sample implementation in {@code UploadFileWithProgressListener}
     *
     * @param name
     * @param bufferedContent
     * @param mimeType
     * @return
     */
    public BufferedSinkProgressHandler addStreamingData(String name,
                                                        final byte[] bufferedContent, final String mimeType) {
        final BufferedSinkProgressHandler[] progressHandler = new BufferedSinkProgressHandler[0];
        singlePartFormBuilder.requestBody = new RequestBody() {

            @Override
            public MediaType contentType() {
                return MediaType.parse(mimeType);
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                progressHandler[0] = new BufferedSinkProgressHandler(sink);
                progressHandler[0].write(bufferedContent);
            }
        };

        return progressHandler[0];
    }

    public RequestBody build() {
        RequestBody requestBody;

        if (!contentType.isMultiPart && singlePartFormBuilder.requestBody != null) {
            requestBody = singlePartFormBuilder.requestBody;

        } else {
            String key, value;
            int size = names.size();

            for (int i = 0; i < size; i++) {
                key = names.get(i);
                value = values.get(i);
                if (contentType.isMultiPart) {
                    multiPartFormBuilder.addFormDataPart(key, value);
                } else {
                    singlePartFormBuilder.addFormData(key, value);
                }
            }

            if (contentType.isMultiPart) {
                requestBody = multiPartFormBuilder.build();
            } else {
                requestBody = singlePartFormBuilder.buildFromParams();
            }
        }

        //Remove all references for faster GC
        multiPartFormBuilder = null;
        singlePartFormBuilder = null;
        names = null;
        values = null;
        contentType = null;

        return requestBody;
    }

    private class SinglePartForm {
        private FormBody.Builder builder = new FormBody.Builder();
        private RequestBody requestBody;

        RequestEntityBuilder buildFromRawData(String rawContent) {
            requestBody = RequestBody.create(contentType.value, rawContent);

            return RequestEntityBuilder.this;
        }

        RequestEntityBuilder buildFromBinaryData(File rawContent)
                throws IOException {
            MediaType mimeType = MediaType.parse(getFileMimeType(rawContent));
            requestBody = RequestBody.create(mimeType, rawContent);

            return RequestEntityBuilder.this;
        }

        void addFormData(String name, String value) {
            builder.add(name, value);
        }

        RequestBody buildFromParams() {
            requestBody = new RequestBody() {
                final MediaType mediaType = contentType.value;

                @Override
                public long contentLength() throws IOException {
                    return builder.build().contentLength();
                }

                @Override
                public MediaType contentType() {
                    return mediaType;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    builder.build().writeTo(sink);
                }
            };

            return requestBody;
        }
    }
}