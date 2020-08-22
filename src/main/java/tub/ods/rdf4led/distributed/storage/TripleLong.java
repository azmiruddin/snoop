package tub.ods.rdf4led.distributed.storage;

/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 27.03.19
 * TODO Description:
 */
public class TripleLong implements Comparable<TripleLong> {

    private long subject;
    private long predicate;
    private long object;



    public TripleLong(long subject, long predicate, long object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }



    public long getSubject() {
        return subject;
    }



    public long getPredicate() {
        return predicate;
    }



    public long getObject() {
        return object;
    }



    @Override
    public int compareTo(TripleLong o) {

        return 0;
    }

}
