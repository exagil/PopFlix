package net.chiragaggarwal.android.popflix.models;

public enum ImageSize {
    SMALL {
        public String decode() {
            return "w185";
        }
    }, MEDIUM {
        public String decode() {
            return "w300";
        }
    }, LARGE {
        public String decode() {
            return "w500";
        }
    };

    public String decode() {
        return this.decode();
    }
}
