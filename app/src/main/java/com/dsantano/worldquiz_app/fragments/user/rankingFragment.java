package com.dsantano.worldquiz_app.fragments.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsantano.worldquiz_app.R;
import com.dsantano.worldquiz_app.fragments.user.dummy.DummyContent;
import com.dsantano.worldquiz_app.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static android.content.ContentValues.TAG;


public class rankingFragment extends Fragment {

    private int mColumnCount = 1;
    private List<Users> usersList;
    private MyrankingRecyclerViewAdapter myrankingRecyclerViewAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context mContext;
    private RecyclerView recyclerView;
    private View view;


    public rankingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ranking_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        usersList = task.getResult().toObjects(Users.class);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                    recyclerView = view.findViewById(R.id.listRanking);

                    myrankingRecyclerViewAdapter = new MyrankingRecyclerViewAdapter(
                            mContext,
                            R.layout.fragment_ranking,
                            usersList);

                    recyclerView.setAdapter(myrankingRecyclerViewAdapter);


                }
            });



        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
