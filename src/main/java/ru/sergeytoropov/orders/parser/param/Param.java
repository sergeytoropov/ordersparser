package ru.sergeytoropov.orders.parser.param;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

/**
 * @author sergeytoropov
 * @since 01.05.18
 */
public final class Param {
    public final List<FileDescriptor> fileDescriptors;

    public Param(List<FileDescriptor> filesDescriptor) {
        this.fileDescriptors = ImmutableList.copyOf(filesDescriptor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Param)) return false;
        Param param = (Param) o;
        return Objects.equals(fileDescriptors, param.fileDescriptors);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fileDescriptors);
    }
}
