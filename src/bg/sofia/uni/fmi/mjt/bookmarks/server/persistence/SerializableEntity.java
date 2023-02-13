package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Serializable;

public abstract class SerializableEntity<K> extends Entity<K>  implements Serializable {

    public SerializableEntity(K key) {
        super(key);
    }
}
