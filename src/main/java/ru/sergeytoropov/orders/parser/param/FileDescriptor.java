package ru.sergeytoropov.orders.parser.param;

import java.nio.file.Path;
import java.util.Objects;

/**
 * @author sergeytoropov
 * @since 01.05.18
 */
public class FileDescriptor {
    public final Path name;
    public final FileType type;

    public FileDescriptor(Path name, FileType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileDescriptor)) return false;
        FileDescriptor that = (FileDescriptor) o;
        return Objects.equals(name, that.name) &&
                type == that.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "FileDescriptor{" +
                "name=" + name +
                ", type=" + type +
                '}';
    }
}
