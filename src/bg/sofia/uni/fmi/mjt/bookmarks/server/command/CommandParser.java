package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import java.util.Arrays;

public class CommandParser {

    private static final String PUNCTUATION_REGEX = "[\\p{Punct}\\s]+";

    public static Command parse(String str) {
        if (str == null) {
            return new UnknownCommand();
        }

        var split =
            Arrays.stream(str.trim().split(PUNCTUATION_REGEX)).filter(x -> !(x.isEmpty() || x.isBlank())).toList();

        if (split.size() < 2) {
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
            case IMPORT_FROM_CHROME -> importFromChrome(args);
            case UNKNOWN -> new UnknownCommand();
        };
    }


    private static CommandType getCommandType(String command) {
        return Arrays.stream(CommandType.values())
            .filter(type -> command.equalsIgnoreCase(type.getName()))
            .findFirst()
            .orElse(CommandType.UNKNOWN);
    }
}
