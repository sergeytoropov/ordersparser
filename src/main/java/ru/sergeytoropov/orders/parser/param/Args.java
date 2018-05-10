package ru.sergeytoropov.orders.parser.param;

import ru.sergeytoropov.orders.parser.OrdersParserException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static ru.sergeytoropov.orders.parser.param.FileType.CSV;
import static ru.sergeytoropov.orders.parser.param.FileType.JSON;

/**
 * @author sergeytoropov
 * @since 01.05.18
 */
public class Args {
    private final String[] args;

    public Args(String[] args) {
        this.args = args;
    }

    public Path createPath(String fileName) throws OrdersParserException {
        Path path = Paths.get(fileName);
        if (Files.exists(path)) {
            return path;
        } else {
            throw new OrdersParserException("Файл [" + path.toAbsolutePath() + "] не найден.");
        }
    }

    public boolean checkExtension(String fileName, FileType type) {
        return fileName.toLowerCase().endsWith(type.getName()) ? true : false;
    }

    public FileDescriptor createFileDescriptor(String fileName, FileType type) throws OrdersParserException {
        return new FileDescriptor(createPath(fileName), type);
    }

    public List<FileDescriptor> getFileDescriptors() throws OrdersParserException {
        List<FileDescriptor> fileDescriptors = new ArrayList<>();
        for (String fileName : args) {
            if (checkExtension(fileName, CSV)) {
                fileDescriptors.add(createFileDescriptor(fileName, CSV));
            } else if (checkExtension(fileName, JSON)) {
                fileDescriptors.add(createFileDescriptor(fileName, JSON));
            } else {
                throw new OrdersParserException("Не поддерживаемый тип файлов [" + fileName + "].");
            }
        }
        if (fileDescriptors.size() > 0) {
            return fileDescriptors;
        } else {
            throw new OrdersParserException("Отсутствуют файлы.");
        }
    }

    public static void usage() {
        System.out.println("usage: fileName ...");
    }
}
