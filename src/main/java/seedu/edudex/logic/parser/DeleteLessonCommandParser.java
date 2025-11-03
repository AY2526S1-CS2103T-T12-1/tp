package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.DeleteLessonCommand;
import seedu.edudex.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLessonCommand object.
 */
public class DeleteLessonCommandParser implements Parser<DeleteLessonCommand> {

    @Override
    public DeleteLessonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+");

        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE));
        }

        String studentRaw = parts[0];
        String lessonRaw = parts[1];

        if (!studentRaw.matches("\\d+") || !lessonRaw.matches("\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE));
        }

        Index studentIndex;
        Index lessonIndex;

        try {
            studentIndex = ParserUtil.parseIndex(studentRaw);
        } catch (ParseException pe) {
            throw new ParseException("Invalid student index");
        }

        try {
            lessonIndex = ParserUtil.parseIndex(lessonRaw);
        } catch (ParseException pe) {
            throw new ParseException("Invalid lesson index");
        }

        return new DeleteLessonCommand(studentIndex, lessonIndex);
    }
}
