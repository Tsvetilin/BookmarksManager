package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.FileRepository;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.Repository;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.observe.BookmarksObserver;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.observe.GroupsObserver;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

public class FileDatabaseContext implements DatabaseContext {
    private final Repository<String, User> usersRepository;
    private final Repository<String, Bookmark> bookmarksRepository;
    private final Repository<String, Group> groupsRepository;

    public FileDatabaseContext(FileRepository<String, User> usersRepository,
                               FileRepository<String, Bookmark> bookmarksRepository,
                               FileRepository<String, Group> groupsRepository) {
        this.usersRepository = Nullable.orDefault(usersRepository, new FileRepository<>(null));
        this.bookmarksRepository = Nullable.orDefault(bookmarksRepository, new FileRepository<>(null));
        this.groupsRepository = Nullable.orDefault(groupsRepository, new FileRepository<>(null));

        ((FileRepository<String, Bookmark>) this.bookmarksRepository).attach(
            new BookmarksObserver(usersRepository, groupsRepository));
        ((FileRepository<String, Group>) this.groupsRepository).attach(
            new GroupsObserver(usersRepository));
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
