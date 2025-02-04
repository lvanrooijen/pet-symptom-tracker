package com.laila.pet_symptom_tracker.mainconfig;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class CustomBanner implements Banner {
    private static final String seaGreen = "\u001B[38;2;0;145;110m";

    private static final String orchidPink = "\u001B[38;2;238;207;212m";
    private static final String chinaRose = "\u001B[38;2;171;79;104m";

    private static final String BANNER =
            orchidPink
                    + "\n"
                    + "██████╗  ██╗  ██╗  █████╗  ██╗  ██╗ ██╗ ██╗   ██╗  █████╗ \n"
                    + "██╔══██╗ ██║  ██║ ██╔══██╗ ██║ ██╔╝ ██║ ╚██╗ ██╔╝ ██╔══██╗\n"
                    + "██║  ██║ ███████║ ███████║ █████╔╝  ██║  ╚████╔╝  ███████║\n"
                    + "██║  ██║ ██╔══██║ ██╔══██║ ██╔═██╗  ██║   ╚██╔╝   ██╔══██║\n"
                    + "██████╔╝ ██║  ██║ ██║  ██║ ██║  ██╗ ██║    ██║    ██║  ██║\n"
                    + "╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝    ╚═╝    ╚═╝  ╚═╝\n"
                    + chinaRose
                    + "Don't forget to have fun! \n\n"
                    + seaGreen;
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(BANNER);
    }
}
