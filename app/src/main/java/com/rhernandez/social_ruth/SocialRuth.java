package com.rhernandez.social_ruth;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.rhernandez.social_ruth.models.PostEntity;
import com.rhernandez.social_ruth.models.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rhernandez on 28/6/18.
 */
public class SocialRuth extends Application {

    public static SocialRuth instance;
    private List<PostEntity> postEntities;
    private List<UserEntity> userEntities;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        SocialRuth.instance = this;
        pref = getApplicationContext().getSharedPreferences("SocialRuth", Context.MODE_PRIVATE);
        editor = pref.edit();
        postEntities = new ArrayList<>();
        userEntities = new ArrayList<>();
        initialUsers();
        initialPost();
        saveUserData();
    }

    public void initialPost() {
        postEntities.add(new PostEntity(userEntities.get(0).getImage(),  userEntities.get(0).getName(), "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRprsx5R3Bii-sbTt1wrNvm0vrwvNItDlNSR77WvLDmYGSu1rso","Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", 24));
        postEntities.add(new PostEntity(userEntities.get(1).getImage(),  userEntities.get(1).getName(),"https://envato-shoebox-0.imgix.net/2a41/93b3-6f8b-4f1c-8767-cd9772b4ded7/kave+310.jpg?w=500&h=278&fit=crop&crop=edges&auto=compress%2Cformat&s=fbc0d75299d7cfda0b3c60ea52ba4aaf","Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", 1054));
        postEntities.add(new PostEntity(userEntities.get(2).getImage(),  userEntities.get(2).getName(), "https://www.motherjones.com/wp-content/uploads/2017/10/blog_life_in_death_2.jpg","Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", 543));
        postEntities.add(new PostEntity(userEntities.get(3).getImage(),  userEntities.get(3).getName(), "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRprsx5R3Bii-sbTt1wrNvm0vrwvNItDlNSR77WvLDmYGSu1rso","Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", 24));
        postEntities.add(new PostEntity(userEntities.get(4).getImage(),  userEntities.get(4).getName(),"https://envato-shoebox-0.imgix.net/2a41/93b3-6f8b-4f1c-8767-cd9772b4ded7/kave+310.jpg?w=500&h=278&fit=crop&crop=edges&auto=compress%2Cformat&s=fbc0d75299d7cfda0b3c60ea52ba4aaf","Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", 1054));
    }


    public void initialUsers() {
        userEntities.add(new UserEntity("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRprsx5R3Bii-sbTt1wrNvm0vrwvNItDlNSR77WvLDmYGSu1rso",  "Clara Campoamor", "8923-1231", "Dios es mi guía"));
        userEntities.add(new UserEntity("http://arqa.com/comunidad/wp-content/uploads/sites/3/avatars/120644/59194d4444e8a-bpfull.jpg", "Diana de Gales", "8923-1231", "Dios es mi guía"));
        userEntities.add(new UserEntity("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXQiLfVGs6DaH5FpGOIN2BT0yLBCzA-csIBBO4xMR7JHo6c6zVIw", "Eleanor Roosevelt", "8923-1231", "Dios es mi guía"));
        userEntities.add(new UserEntity("https://media.licdn.com/dms/image/C4D03AQFhCGJT_aInjw/profile-displayphoto-shrink_200_200/0?e=1535587200&v=beta&t=YZTd-3EfBIeqBWO07h5Qie-uwe3PuNnCDFOUR4_DYy8", "Matthew Campoamor", "8923-1231", "Dios es mi guía"));
        userEntities.add(new UserEntity("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTg4RX51gJc6rYzqoBZg_s8mAciR3nfkcoHM2rrN_mREhUtvf5jOQ", "Paulus Roosevelt", "8923-1231", "Dios es mi guía"));
    }

    public List<PostEntity> getPostByUserName(String userName) {
        List<PostEntity> userPosts = new ArrayList<>();
        for (PostEntity entity : postEntities) {
            if (entity.getUserName().equalsIgnoreCase(userName)) {
                userPosts.add(entity);
            }
        }
        return userPosts;
    }

    public void saveUserData() {
        saveData("name", "Amelia Earhart");
        saveData("phone", "83231221");
        saveData("state", "Fingir que no duele, duele el doble.");
    }

    public static SocialRuth getInstance() {
        return SocialRuth.instance;
    }

    public List<PostEntity> getPostEntities() {
        return postEntities;
    }

    public void setPostEntities(List<PostEntity> postEntities) {
        this.postEntities = postEntities;
    }

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public void saveData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void removeData(String key) {
        editor.remove(key);
        editor.commit();
    }

    public String getValue(String key) {
        return pref.getString(key, null);
    }
}
