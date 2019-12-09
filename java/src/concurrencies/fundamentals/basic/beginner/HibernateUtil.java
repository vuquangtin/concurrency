package basic.beginner;
/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class HibernateUtil {
//	 
//    private static final Log log = LogFactory.getLog(HibernateUtil.class);
//    /**
//     * Read the configuration, will share across threads*
//     */
//    private static SessionFactory sessionFactory;
//    /**
//     * the per thread session *
//     */
//    private static final ThreadLocal<Session> currentSession = new ThreadLocal<Session>();
//    /**
//     * The constants for describing the ownerships *
//     */
//    private static final Owner trueOwner = new Owner(true);
//    private static final Owner fakeOwner = new Owner(false);
//    /**
//     * set this to false to test with JUnit *
//     */
//    private static final boolean isLife = true;
// 
//    /**
//     * get the hibernate session and set it on the thread local. Returns
//     * trueOwner if it actually opens a session
//     */
//    public static Object createSession() throws Exception {
//        Session session = (Session) currentSession.get();
//        //System.out.println(session);
//        if (session == null) {
//            //System.out.println(&quot;No Session Found - Create and give the identity&quot;);
//            session = getSessionFactory().openSession();
//            currentSession.set(session);
//            return trueOwner;
//        }
//        //System.out.println(&quot;Session Found - Give a Fake identity&quot;);
//        return fakeOwner;
    }