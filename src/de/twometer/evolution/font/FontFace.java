package de.twometer.evolution.font;

import de.twometer.evolution.res.Loader;

import java.io.IOException;
import java.util.Map;

class FontFace {

    private static final String FONTS_DIRECTORY = "fonts/";

    private int fontTexture;

    private Map<Integer, Glyph> glyphs;

    private FontFace(int fontTexture, Map<Integer, Glyph> glyphs) {
        this.fontTexture = fontTexture;
        this.glyphs = glyphs;
    }

    static FontFace load(String name) throws IOException {
        int texture = Loader.loadTexture(FONTS_DIRECTORY + name + ".png");
        Map<Integer, Glyph> glyphs = FontFileParser.fromFile(FONTS_DIRECTORY + name + ".fnt").parse();
        return new FontFace(texture, glyphs);
    }

    Glyph getGlyph(char chr) {
        return glyphs.get((int) chr);
    }

    int getFontTexture() {
        return fontTexture;
    }
}
