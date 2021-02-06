package com.ijiagoushi.chillies.http;

import com.ijiagoushi.chillies.core.http.ContentType;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Form Body
 *
 * @author miles.tang
 */
public final class HttpFormBody extends HttpRequestBody {

    private final List<Item> items;
    private final Charset charset;

    HttpFormBody(List<Item> items, Charset charset) {
        this.items = Collections.unmodifiableList(items);
        this.contentType = ContentType.APPLICATION_FORM_URLENCODED;
        this.charset = charset;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<Item> items = new ArrayList<>();

        private final Charset charset;

        public Builder() {
            this(null);
        }

        public Builder(Charset charset) {
            this.charset = CharsetUtil.getCharset(charset, CharsetUtil.UTF_8);
        }

        public Builder add(@NotNull String name, @NotNull String value) {
            Preconditions.requireNonNull(name, "'name' must not be null");
            Preconditions.requireNonNull(value, "'value' must not be null");

            items.add(new Item(name, value));
            return this;
        }

        public HttpFormBody build() {
            return new HttpFormBody(items, charset);
        }
    }

    public static class Item {

        private final String name;

        private final String value;

        Item(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

    }

}
