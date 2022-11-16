package net.javahub.musc.resources;

import java.io.IOException;

@FunctionalInterface
interface Resource {
    void getResource() throws IOException;
}
