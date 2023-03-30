//package com.example.managementapp.api;
//
//import com.example.androidapp.AddContact;
//import com.example.androidapp.Addmsg;
//import com.example.androidapp.Contact;
//import com.example.androidapp.Invitation;
//import com.example.androidapp.Message;
//import com.example.androidapp.Transfer;
//import com.example.androidapp.User;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.Path;
//import retrofit2.http.Query;
//
//public interface WebServiceAPI {
//    static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
//
//    @POST("Users/login/{username}/{password}")
//    Call<User> login(@Path("username") String loginUsername,@Path("password") String loginPassword);
//
//    @POST("Users/addToken/{username}/{token}")
//    Call<Void> addToken(@Path("username") String username, @Path("token") String token);
//
//    @POST("Users/signUp")
//    Call<Void> signup(@Query("username") String username,@Query("nickname") String nickname,@Query("password") String password);
//
//    @GET("Users/messages")
//    Call<List<Message>> getmessageofuser(@Query("connecteduser") String username);
//
//    @GET("contacts")
//    Call<List<Contact>> getcontacts(@Query("connecteduser") String username);
//
//    @GET("contacts/{contactname}/messages")
//    Call<List<Message>> getmessages(@Path("contactname") String contactname,@Query("connecteduser") String username);
//
//    @POST("contacts/{contactname}/messages")
//    Call<Void> newmessage(@Path("contactname") String contactname2, @Body Addmsg addmsg);
//
//    @POST("contacts")
//    Call<Void> addcontact(@Body AddContact addcontact);
//
//    @POST("transfer")
//    Call<Void> transfer(@Body Transfer transfermsg);
//
//    @GET("transfer")
//    Call<String> getconnecteduser();
//
//    @POST("invitations")
//    Call<Void> invitation(@Body Invitation invitationmsg);
//
//
////    @DELETE("posts/{id}")
////    Call<Void> deletePost(@Path("id") int id);
//}
