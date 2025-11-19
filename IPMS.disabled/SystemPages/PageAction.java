package IPMS.SystemPages;

public class PageAction {

    public enum Type { PUSH, POP, EXIT }

    private Type type;
    private Page nextPage;

    public static PageAction push(Page p) {
        PageAction a = new PageAction();
        a.type = Type.PUSH;
        a.nextPage = p;
        return a;
    }

    public static PageAction pop() {
        PageAction a = new PageAction();
        a.type = Type.POP;
        return a;
    }

    public static PageAction exit() {
        PageAction a = new PageAction();
        a.type = Type.EXIT;
        return a;
    }

    public Type gettype() {
        return type;
    }

    public Page getnextPage() {
        return nextPage;
    }
}

