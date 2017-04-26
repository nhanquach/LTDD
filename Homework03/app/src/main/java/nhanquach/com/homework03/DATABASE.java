package nhanquach.com.homework03;

import java.util.ArrayList;

/**
 * Created by mac on 2/16/17.
 */

public class DATABASE {

    public String[] text = {"Pesto Turkey Meatballs",
            "Chicken Parmesan", "Green Herb Pancake", "Roasted salmon with green herbs",
    "Red Velvet Heart Pancakes", "Pork chops on grill"};
    public Integer[] icon = {R.drawable.meatball, R.drawable.chicken_parmesan, R.drawable.green_herb_pancake,
    R.drawable.roasted_salmon, R.drawable.red_velvet, R.drawable.pork};

    public class DbRecord {
        public String text;
        public Integer icon;

        public DbRecord(String text, Integer icon) {
            this.text = text;
            this.icon = icon;
        }
    }

    public ArrayList<DbRecord> dbList = new ArrayList<DbRecord>();

    public DATABASE(){
        for (int i=0; i<text.length; i++){
            dbList.add(new DbRecord(text[i], icon[i]));
        }
    }
}
