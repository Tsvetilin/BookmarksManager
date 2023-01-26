package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

public class FileDatabaseContext implements DatabaseContext {

    private final Repository<String, User> usersRepository;
    private final Repository<String, Bookmark> bookmarksRepository;
    private final Repository<String, Group> groupsRepository;

    // TODO: add serializer for config
    public FileDatabaseContext(Repository<String, User> usersRepository,
                               Repository<String, Bookmark> bookmarksRepository,
                               Repository<String, Group> groupsRepository) {
        this.usersRepository = Nullable.orDefault(usersRepository, new FileRepository<>(null));
        this.bookmarksRepository = Nullable.orDefault(bookmarksRepository, new FileRepository<>(null));
        this.groupsRepository = Nullable.orDefault(groupsRepository, new FileRepository<>(null));
    }

    @Override
    public Repository<String, User> users() {
        return usersRepository;
    }

    @Override
    public Repository<String, Bookmark> bookmarks() {
        return bookmarksRepository;
    }

    @Override
    public Repository<String, Group> groups() {
        return groupsRepository;
    }

    @Override
    public void persist() {
        this.usersRepository.persist();
        this.groupsRepository.persist();
        this.bookmarksRepository.persist();
    }
}
