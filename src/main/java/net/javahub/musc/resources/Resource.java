package net.javahub.musc.resources;

import java.io.IOException;

@FunctionalInterface
public interface Resource {
    void getResource() throws IOException;
}
