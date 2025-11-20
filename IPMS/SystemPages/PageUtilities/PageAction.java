package IPMS.SystemPages.PageUtilities;

public class PageAction {

    public enum Type { PUSH, POP, STAY, EXIT }

    private Type type;
    private Page nextPage;

    /** 
     * @param p
     * @return PageAction
     */
    public static PageAction push(Page p) {
        PageAction a = new PageAction();
        a.type = Type.PUSH;
        a.nextPage = p;
        return a;
    }

    /** 
     * @return PageAction
     */
    public static PageAction pop() {
        PageAction a = new PageAction();
        a.type = Type.POP;
        return a;
    }

    /** 
     * @return PageAction
     */
    public static PageAction stay() {
        PageAction a = new PageAction();
        a.type = Type.STAY;
        return a;
    }

    /** 
     * @return PageAction
     */
    public static PageAction exit() {
        PageAction a = new PageAction();
        a.type = Type.EXIT;
        return a;
    }

    /** 
     * @return Type
     */
    public Type gettype() {
        return type;
    }

    /** 
     * @return Page
     */
    public Page getnextPage() {
        return nextPage;
    }
}

