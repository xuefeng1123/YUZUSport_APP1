package edu.bjtu.example.sportsdashboard;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;



public class CourseListFragment extends ListFragment{
    String[] cname = {"高效格斗燃脂，好看又能打",
            "六个诀窍，高效增肌",
            "明星秘籍，七天上镜计划",
            "6套动作，练就纤细大长腿",
            "轻松解锁超酷倒立技能",
            "产后幸福辣妈私房课",
            "快走10年竟看不出她48岁了"};
    String[] cintro = {
            "拳力以赴，好身材打出来！",
            "掌握高效动作技巧，避免误区轻松增肌",
            "美丽气质肩背养成记！",
            "细直美腿，从走路开始！",
            "跟着老师突破自己，换个角度看世界",
            "辣妈必备的产后恢复清单！",
            "中老年也可参考的快走塑身方案"
    };
    String[] cprice = {
            "¥18","¥12","¥12","¥15","¥30","¥18","¥12"
    };
    String[] clearn = {
            "5节课/1121次学习","5节课/3316次学习","6节课/6710次学习","5节课/2899次学习","10节课/1562次学习","7节课/3544次学习","5节课/893次学习"
    };
    int[] images = {R.drawable.trainer5, R.drawable.course2, R.drawable.course3, R.drawable.course4, R.drawable.course5, R.drawable.course6, R.drawable.course7};

    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        //MAP
        HashMap<String, String> map = new HashMap<String, String>();

        //FILL
        for (int i = 0; i < cname.length; i++) {
            map = new HashMap<String, String>();
            map.put("Name", cname[i]);
            map.put("Intro",cintro[i]);
            map.put("Price", cprice[i]);
            map.put("Learn", clearn[i]);
            map.put("Image", Integer.toString(images[i]));

            data.add(map);
        }

        //KEYS IN MAP
        String[] from = {"Name","Intro", "Price","Learn","Image"};

        //IDS OF VIEWS
        int[] to = {R.id.cnameTxt, R.id.cintroTxt,R.id.cpriceTxt,R.id.clearnTxt, R.id.cimageView};

        //ADAPTER
        adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.courselist_item, from, to);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        getListView().setOnItemClickListener(new OnItemClickListener() {

            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(CourseListFragment.this.getContext(),VedioActivity.class);
                startActivityForResult(intent,0);
            }
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub

                Toast.makeText(getActivity(), data.get(pos).get("Name"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CourseListFragment.this.getActivity(),VedioActivity.class);
                startActivity(intent);
            }
        });
    }

}
