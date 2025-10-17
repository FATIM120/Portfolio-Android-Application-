package com.example.portfolio;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // Récupère le profil ; en passant "notes=true", on obtient aussi la liste des notes
    @GET("api/profile")
    Call<ApiResponse> getProfile(@Query("notes") String notes);

    // Met à jour le profil via POST /api/profile/update
    @POST("api/profile/update")
    Call<Void> updateProfile(@Body ApiResponse profile);

    // Ajoute une nouvelle note via POST /api/addNote
    @POST("api/addNote")
    Call<AddNoteResponse> addNote(@Body Note note);
}
