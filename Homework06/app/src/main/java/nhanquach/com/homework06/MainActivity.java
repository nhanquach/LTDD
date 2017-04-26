package nhanquach.com.homework06;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Location> locationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LocationAdapter mAdapter;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mAdapter = new LocationAdapter(locationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(30);

        prepareLocation();

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(
                getApplicationContext(), recyclerView, new ClickListener()
        {
            @Override
            public void onClick(View view, int position) {
                showBottomSheet(locationList.get(position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void showBottomSheet(final Location l){
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            recyclerView.setBackgroundColor(Color.LTGRAY);

            TextView info = (TextView) findViewById(R.id.text1);
            Button gotoSite = (Button) findViewById(R.id.btn2);
            Button mapit = (Button) findViewById(R.id.btn1);

            info.setText(l.getName());
            gotoSite.setText("Website: \n "+l.getWebsite());
            mapit.setText("Show on map\n"+l.getAddress());

            gotoSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(l.getWebsite().toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            mapit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("latitude", l.getCoor().latitude);
                    intent.putExtra("longtitude", l.getCoor().longitude);
                    intent.putExtra("name", l.getName());
                    startActivity(intent);
                }
            });


        }
        else {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            recyclerView.setBackgroundColor(Color.WHITE);
        }
    }

    private void prepareLocation() {
        Location location = new Location("Trường Đại học Sài Gòn", "273 An Dương Vường, phường 3, Quận 5, Hồ Chí Minh",
                "http://www.sgu.edu.vn", new LatLng(10.759756, 106.682342) );
        locationList.add(location);

        location = new Location("Trường Đại học Sư phạm ", "280 An Dương Vương, phường 4, Quận 5, Hồ Chí Minh"
                , "http://www.hcmup.edu.vn", new LatLng(10.761464, 106.682240));
        locationList.add(location);

        location = new Location("Trường Đại học Ngoại thương", "15 Đường D5, Phường 25, Bình Thạnh, Hồ Chí Minh",
                "http://www.ftu.edu.vn", new LatLng(10.807017, 106.713121));
        locationList.add(location);

        location = new Location("Trường Đại học Bách Khoa TPHCM", "268 Lý Thường Kiệt, phường 14, Quận 10, Hồ Chí Minh",
                "http://www.hcmut.edu.vn/vi", new LatLng(10.772832, 106.659986));
        locationList.add(location);

        location = new Location("Trường Đại học Hoa Sen", "8 Nguyễn Văn Tráng, Bến Thành, Quận 1, Hồ Chí Minh",
                "http://www.hoasen.edu.vn", new LatLng(10.770558, 106.692538));
        locationList.add(location);

        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}