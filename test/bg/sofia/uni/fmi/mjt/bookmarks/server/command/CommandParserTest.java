package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.SecureString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandParserTest {

    @Test
    void testParseEmptyCommand() {
        Command cmd = CommandParser.parse(new SecureString("  "));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseInvalidCommand() {
        Command cmd = CommandParser.parse(new SecureString("invalid command"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseHelpCommand() {
        Command cmd = CommandParser.parse(new SecureString("help"));
        assertEquals(CommandType.HELP, cmd.getType(), "Should map help command to Help cmd.");
    }

    @Test
    void testParseHelpCommandInvalid() {
        Command cmd = CommandParser.parse(new SecureString("help with args"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map help command to Unknown cmd.");
    }

    @Test
    void testParseLoginCommand() {
        Command cmd = CommandParser.parse(new SecureString("login username pass"));
        assertEquals(CommandType.LOGIN, cmd.getType(), "Should map login command to Login cmd.");
    }

    @Test
    void testParseInvalidLoginCommand() {
        Command cmd = CommandParser.parse(new SecureString("login with different amount of args"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseRegisterCommand() {
        Command cmd = CommandParser.parse(new SecureString("register un pw"));
        assertEquals(CommandType.REGISTER, cmd.getType(), "Should map register command to Register cmd.");
    }

    @Test
    void testParseInvalidRegisterCommand() {
        Command cmd = CommandParser.parse(new SecureString("register noargs"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseLogoutCommand() {
        Command cmd = CommandParser.parse(new SecureString("logout"));
        assertEquals(CommandType.LOGOUT, cmd.getType(), "Should map logout command to Logout cmd.");
    }

    @Test
    void testParseInvalidLogoutCommand() {
        Command cmd = CommandParser.parse(new SecureString("logout with rnd params"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseAddToCommand() {
        Command cmd = CommandParser.parse(new SecureString("add-to group url"));
        assertEquals(CommandType.ADD_TO, cmd.getType(), "Should map command to AddTo cmd.");
    }

    @Test
    void testParseAddToInvalidCommand() {
        Command cmd = CommandParser.parse(new SecureString("add-to with maaany maany args"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseImportFromChromeCommand() {
        Command cmd = CommandParser.parse(new SecureString("import-from-chrome url"));
        assertEquals(CommandType.IMPORT_FROM_CHROME, cmd.getType(), "Should map command to ImportFromChrome cmd.");
    }

    @Test
    void testParseImportFromChromeInvalidCommand() {
        Command cmd = CommandParser.parse(new SecureString("import-from-chrome"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }


    @Test
    void testParseListInvalidArgsCommand() {
        Command cmd = CommandParser.parse(new SecureString("list args"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map command to Unknown cmd.");
    }

    @Test
    void testParseListGroupCommand() {
        Command cmd = CommandParser.parse(new SecureString("list --group-name groupname"));
        assertEquals(CommandType.LIST, cmd.getType(), "Should map command to List cmd.");
    }

    @Test
    void testParseListGroupInvalidArgsCommand() {
        Command cmd = CommandParser.parse(new SecureString("list--groupname groupname and other args"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map command to Unknown cmd.");
    }

    @Test
    void testParseListGroupNoGroupCommand() {
        Command cmd = CommandParser.parse(new SecureString("list--groupname "));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map command to Unknown cmd.");
    }


    @Test
    void testParseNewGroupCommand() {
        Command cmd = CommandParser.parse(new SecureString("new-group group"));
        assertEquals(CommandType.NEW_GROUP, cmd.getType(), "Should map command to NewGroup cmd.");
    }


    @Test
    void testParseNewGroupInvalidCommand() {
        Command cmd = CommandParser.parse(new SecureString("new-group too many args"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }


    @Test
    void testParseRemoveFromCommand() {
        Command cmd = CommandParser.parse(new SecureString("remove-from group url"));
        assertEquals(CommandType.REMOVE_FROM, cmd.getType(), "Should map command to RemoveFrom cmd.");
    }


    @Test
    void testParseRemoveFromInvalidCommand() {
        Command cmd = CommandParser.parse(new SecureString("remove-from wherewhat"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseSearchTitleCommand() {
        Command cmd = CommandParser.parse(new SecureString("search --title title"));
        assertEquals(CommandType.SEARCH, cmd.getType(), "Should map command to Search cmd.");
    }

    @Test
    void testParseSearchTagCommand() {
        Command cmd = CommandParser.parse(new SecureString("search --tags tag"));
        assertEquals(CommandType.SEARCH, cmd.getType(), "Should map command to Search cmd.");
    }

    @Test
    void testParseSearchTagsManyCommand() {
        Command cmd = CommandParser.parse(new SecureString("search --tags title tag another"));
        assertEquals(CommandType.SEARCH, cmd.getType(), "Should map command to Search cmd.");
    }

    @Test
    void testParseInvalidSearchCommand() {
        Command cmd = CommandParser.parse(new SecureString("search   "));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseInvalidSearchTitleCommand() {
        Command cmd = CommandParser.parse(new SecureString("search --title"));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

    @Test
    void testParseInvalidSearchTagsCommand() {
        Command cmd = CommandParser.parse(new SecureString("search --tags "));
        assertEquals(CommandType.UNKNOWN, cmd.getType(), "Should map invalid command to Unknown cmd.");
    }

}
