package fr.iutnc.info.muller624u.todolist_muller_geoffroy_3;

/**
 * Created by phil on 06/02/17.
 */

public class TodoItem {

    public enum Tags {
        Faible("Faible"), Normal("Normal"), Important("Important");

        private String desc;
        Tags(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    private String label;
    private Tags tag;
    private boolean done;
    private int id;

    public TodoItem(Tags tag, String label) {
        this.tag = tag;
        this.label = label;
        this.done = false;
        this.id = -1;
    }

    public TodoItem(String label, Tags tag, boolean done) {
        this.label = label;
        this.tag = tag;
        this.done = done;
        this.id = -1;
    }

    public TodoItem(Tags tag, String label, int idp) {
        this.tag = tag;
        this.label = label;
        this.done = false;
        this.id = idp;
    }

    public TodoItem(String label, Tags tag, boolean done, int idp) {
        this.label = label;
        this.tag = tag;
        this.done = done;
        this.id = idp;
    }

    public static Tags getTagFor(String desc) {
        for (Tags tag : Tags.values()) {
            if (desc.compareTo(tag.getDesc()) == 0)
                return tag;
        }

        return Tags.Faible;
    }

    public String getLabel() {
        return label;
    }

    public int getId(){
        return this.id;
    }

    public Tags getTag() {
        return tag;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
