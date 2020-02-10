public class Lexeme {
    private String value;
    private LexemesType type;

    public Lexeme(String value, LexemesType type) {
        this.value = value;
        this.type = type;
    }

    public Lexeme(Character value, LexemesType type) {
        this.value = value.toString();
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LexemesType getType() {
        return type;
    }

    public void setType(LexemesType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }

}
