package com.evgenii.waterfootprint.mocks;

import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.io.IOException;

public class AssetsFileReaderMock implements AssetsFileReaderInterface {
    public String mockReadFileFileNameArgument;
    public String mockReadFileReturnValue;

    @Override
    public String ReadFile(String fileName) throws IOException {
        mockReadFileFileNameArgument = fileName;
        return mockReadFileReturnValue;
    }
}
