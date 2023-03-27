package ro.ubb;

import ro.ubb.domain.HasID;
import ro.ubb.repository.AbstractXMLRepository;

import java.util.Iterator;

public class TestUtils {
    public static void clearRepo(AbstractXMLRepository<String, ? extends HasID<String>> fileRepo) {
        for (HasID<String> item : fileRepo.findAll()) {
            fileRepo.delete(item.getID());
        }
    }

    public static int getIteratorCnt(Iterator<?> iterator){
        int count = 0;
        while(iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}
