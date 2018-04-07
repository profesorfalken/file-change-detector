package com.profesorfalken;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileChangeDetectorTest {
    @Test
    public void TestRegisteAllDirectories() throws IOException {
        FileChangeDetector fileChangeDetector = new FileChangeDetector();

        fileChangeDetector.registerAllDirectories();
    }
}