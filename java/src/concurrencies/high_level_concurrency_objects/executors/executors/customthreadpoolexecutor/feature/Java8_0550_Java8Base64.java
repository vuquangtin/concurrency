package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.Base64;
import java.util.UUID;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Java8_0550_Java8Base64 {

	public static void main(String args[]) {

		new Java8_0550_Java8Base64().java8Base64();

		new Java8_0550_Java8Base64().java8Base64_();

	}

	void java8Base64() {
		try {

			// Encode using basic encoder
			String base64encodedString = Base64.getEncoder().encodeToString("TutorialsPoint?java8".getBytes("utf-8"));
			out.println("Base64 Encoded String (Basic) :" + base64encodedString);

			// Decode
			byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
			out.println("Original String: " + new String(base64decodedBytes, "utf-8"));
			
			base64encodedString = Base64.getUrlEncoder().encodeToString("TutorialsPoint?java8".getBytes("utf-8"));
			out.println("Base64 Encoded String (URL) :" + base64encodedString);

			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < 10; ++i) {
				stringBuilder.append(UUID.randomUUID().toString());
			}

			byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
			String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
			out.println("Base64 Encoded String (MIME) :" + mimeEncodedString);
		} catch (UnsupportedEncodingException e) {
			out.println("Error :" + e.getMessage());
		}
	}


	void java8Base64_() {
        final String text = "\n\nBase64 finally in Java 8!";
        final String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        out.println(encoded);
        final String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        out.println(decoded);
	}

}
