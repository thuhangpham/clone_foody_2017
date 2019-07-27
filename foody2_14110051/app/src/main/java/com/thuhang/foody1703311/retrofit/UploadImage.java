package com.thuhang.foody1703311.retrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by thuha on 5/14/2017.
 */

public class UploadImage{
    private IMyService mService;
    public UploadImage(){
        this.mService = ApiUtils.getService();
    }
//    private void uploadFile(Uri fileUri) {
//        // MultipartBody.Part is used to send also the actual file name
//        MultipartBody.Part body = prepareFilePart("upload", fileUri);
//        // create a map of data to pass along
//        RequestBody description = createPartFromString("hello, this is description speaking");
//        RequestBody place = createPartFromString("Magdeburg");
//        RequestBody time = createPartFromString("2016");
//
//        HashMap<String, RequestBody> map = new HashMap<>();
//        map.put("description", description);
//        map.put("place", place);
//        map.put("time", time);
//        // finally, execute the request
//        Call<ResponseBody> call = mService.uploadItem(map, body);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call,
//                                   Response<ResponseBody> response) {
//                Log.v("Upload", "success");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("Upload error:", t.getMessage());
//            }
//        });
//    }
    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        Log.e("fileURI",""+fileUri);
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
