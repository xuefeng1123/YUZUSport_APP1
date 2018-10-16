package edu.bjtu.example.sportsdashboard;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentListFragment extends ListFragment {

    String[] trainers = {"许愿玉藻前", "开黑吗我宫本贼6", "我", "松香且华丽", "悠闲而小明", "Van！！", "WBY"};
    String[] comments = {
            "新的月计划开始，上个月瘦了13斤，坚持下去，看看这个月的变化吧！",
            "比跑步瘦得快。。",
            "为什么我坚持半个月了，还肥了两斤",
            "马甲线养成中...",
            "课程实用！无论减脂还是塑形都有效果",
            "训练量好大啊",
            "羽生夫人D8打卡！"
    };
    int[] images = {R.drawable.trainer1, R.drawable.trainer2, R.drawable.trainer3, R.drawable.trainer4, R.drawable.trainer5, R.drawable.trainer6, R.drawable.athelet_1};

    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        //MAP
        HashMap<String, String> map = new HashMap<String, String>();

        //FILL
        for (int i = 0; i < trainers.length; i++) {
            map = new HashMap<String, String>();
            map.put("Trainers", trainers[i]);
            map.put("Comments",comments[i]);
            map.put("Image", Integer.toString(images[i]));

            data.add(map);
        }

        //KEYS IN MAP
        String[] from = {"Trainers","Comments", "Image"};

        //IDS OF VIEWS
        int[] to = {R.id.nameTxt_Com, R.id.comTxt, R.id.imageView_Com};

        //ADAPTER
        adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.commentlist_item, from, to);
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub

                Toast.makeText(getActivity(), data.get(pos).get("Trainers"), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
