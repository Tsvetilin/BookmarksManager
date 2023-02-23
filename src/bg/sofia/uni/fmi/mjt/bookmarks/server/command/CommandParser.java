package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.server.command.account.LoginCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.account.LogoutCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.account.RegisterCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.AddToCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.CleanupCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.ImportFromChromeCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.ListByGroupCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.ListCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.NewGroupCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.RemoveFromCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.SearchByTagsCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks.SearchByTitleCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.common.HelpCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.common.UnknownCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.SecureString;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommandParser {

    private static final char WHITESPACE = ' ';

    private static final String GROUP_NAME_PARAM = "--group-name";
    private static final String SHORTEN_PARAM = "--shorten";
    private static final String TITLE_PARAM = "--title";
    private static final String TAGS_PARAM = "--tags";
    private static final int ARGS_SIZE_0 = 0;
    private static final int ARGS_SIZE_1 = 1;
    private static final int ARGS_SIZE_2 = 2;
    private static final int ARGS_SIZE_3 = 3;

    public static Command parse(SecureString str) {
        Nullable.throwIfNull(str);

        var split = str.split(WHITESPACE).stream().filter(Predicate.not(SecureString::isBlank)).toList();

        if (split.size() == 0) {
            return new UnknownCommand();
        }

        var command = split.stream().findFirst().get();
        var args = split.stream().skip(1).toList();

        var commandType = getCommandType(command.toString());
        return switch (commandType) {
            case HELP -> help(getStringList(args));
            case REGISTER -> register(args);
            case LOGIN -> login(args);
            case LOGOUT -> logout(getStringList(args));
            case NEW_GROUP -> newGroup(getStringList(args));
            case ADD_TO -> addTo(getStringList(args));
            case REMOVE_FROM -> removeFrom(getStringList(args));
            case LIST -> list(getStringList(args));
            case SEARCH -> search(getStringList(args));
            case CLEANUP -> cleanup(getStringList(args));
            case IMPORT_FROM_CHROME -> importFromChrome(str.toString());
            case UNKNOWN -> new UnknownCommand();
        };
    }

    private static List<String> getStringList(List<SecureString> list) {
        return list.stream().map(SecureString::toString).toList();
    }

    private static Command importFromChrome(String cmd) {
        if (cmd.trim().equals(CommandType.IMPORT_FROM_CHROME.getName())) {
            return new UnknownCommand("Missing url. Cannot add chrome bookmark.");
        }

        return new ImportFromChromeCommand(
            cmd
                .substring(CommandType.IMPORT_FROM_CHROME.getName().length() + 1)
                .trim()
        );
    }

    private static Command cleanup(List<String> args) {
        if (args.size() != ARGS_SIZE_0) {
            return new UnknownCommand("Invalid arguments count. Cleanup command has no arguments.");
        }

        return new CleanupCommand();
    }

    private static Command list(List<String> args) {
        if (args.size() == ARGS_SIZE_0) {
            return new ListCommand();
        }

        if (args.size() == ARGS_SIZE_2 && args.get(0).equals(GROUP_NAME_PARAM)) {
            return new ListByGroupCommand(args.get(1));
        }

        return new UnknownCommand("Invalid argument for command.");
    }

    private static Command search(List<String> args) {
        if (args.size() < ARGS_SIZE_2) {
            return new UnknownCommand();
        }

        if (args.get(0).equals(TITLE_PARAM)) {
            return new SearchByTitleCommand(args.stream().skip(1).collect(Collectors.joining(" ")));
        }

        if (args.get(0).equals(TAGS_PARAM)) {
            return new SearchByTagsCommand(args.stream().skip(1).collect(Collectors.toList()));
        }

        return new UnknownCommand();
    }

    private static Command removeFrom(List<String> args) {
        if (args.size() != ARGS_SIZE_2) {
            return new UnknownCommand("Invalid arguments count. Command requires group name and bookmark.");
        }

        return new RemoveFromCommand(args.get(0), args.get(1));
    }

    private static Command newGroup(List<String> args) {
        if (args.size() != ARGS_SIZE_1) {
            return new UnknownCommand("Invalid arguments count. Command requires only group name.");
        }

        return new NewGroupCommand(args.get(0));
    }

    private static Command addTo(List<String> args) {
        if (args.size() == ARGS_SIZE_2) {
            return new AddToCommand(args.get(1), args.get(0), false);
        }

        if (args.size() == ARGS_SIZE_3 && args.get(2).equals(SHORTEN_PARAM)) {
            return new AddToCommand(args.get(1), args.get(0), true);
        }

        return new UnknownCommand(
            "Invalid arguments count. Valid syntax is: add-to <group-name> <bookmark> {--shorten} ");
    }

    private static Command login(List<SecureString> args) {
        if (args.size() != ARGS_SIZE_2) {
            return new UnknownCommand("Invalid arguments count. Command requires username and password.");
        }

        var result = new LoginCommand(args.get(0).toString(), args.get(1));
        args.get(1).clear();
        return result;
    }

    private static Command logout(List<String> args) {
        if (args.size() != ARGS_SIZE_0) {
            return new UnknownCommand("Invalid arguments count. Logout command has no arguments.");
        }

        return new LogoutCommand();
    }

    private static Command register(List<SecureString> args) {
        if (args.size() != ARGS_SIZE_2) {
            return new UnknownCommand("Invalid arguments count. Command requires username and password.");
        }

        var result = new RegisterCommand(args.get(0).toString(), args.get(2));
        args.get(0).clear();
        return result;
    }

    private static Command help(List<String> args) {
        if (args.size() != ARGS_SIZE_0) {
            return new UnknownCommand("Invalid arguments count. Help command has no arguments");
        }

        return new HelpCommand();
    }


    private static CommandType getCommandType(String command) {
        return Arrays.stream(CommandType.values())
            .filter(type -> command.equalsIgnoreCase(type.getName()))
            .findFirst()
            .orElse(CommandType.UNKNOWN);
    }
}
