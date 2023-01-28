package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.server.command.common.UnknownCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

import java.util.Arrays;
import java.util.List;

public class CommandParser {

    private static final String PUNCTUATION_REGEX = "[\\p{Punct}\\s]+";

    public static Command parse(String str) {
        Nullable.throwIfNull(str);

        var split =
            Arrays.stream(str.trim().split(PUNCTUATION_REGEX)).filter(x -> !(x.isEmpty() || x.isBlank())).toList();

        if (split.size() == 0) {
            return new UnknownCommand();
        }

        var command = split.stream().findFirst().get();
        var args = split.stream().skip(1).toList();

        var commandType = getCommandType(command);
        return switch (commandType) {
            case HELP -> help(args);
            case REGISTER -> register(args);
            case LOGIN -> login(args);
            case LOGOUT -> logout(args);
            case NEW_GROUP -> newGroup(args);
            case ADD_TO -> addTo(args);
            case REMOVE_FROM -> removeFrom(args);
            case LIST -> list(args);
            case SEARCH -> search(args);
            case CLEANUP -> cleanup(args);
            case IMPORT_FROM_CHROME -> importFromChrome(str);
            case UNKNOWN -> new UnknownCommand();
        };
    }

    private static Command importFromChrome(String cmd) {
        return null;
    }

    private static Command cleanup(List<String> args) {
        return null;
    }

    private static Command list(List<String> args) {
        return null;
    }

    private static Command search(List<String> args) {
        return null;
    }

    private static Command removeFrom(List<String> args) {
        return null;
    }

    private static Command newGroup(List<String> args) {
        return null;
    }

    private static Command addTo(List<String> args) {
        return null;
    }

    private static Command login(List<String> args) {
        return null;
    }

    private static Command logout(List<String> args) {
        return null;
    }

    private static Command register(List<String> args) {
        return null;
    }

    private static Command help(List<String> args) {
        return null;
    }


    private static CommandType getCommandType(String command) {
        return Arrays.stream(CommandType.values())
            .filter(type -> command.equalsIgnoreCase(type.getName()))
            .findFirst()
            .orElse(CommandType.UNKNOWN);
    }
}
