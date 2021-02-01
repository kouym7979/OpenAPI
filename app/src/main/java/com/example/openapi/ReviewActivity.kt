package com.example.openapi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.openapi.Adapter.ReviewAdapter
import com.example.openapi.Data.BookInfo
import com.example.openapi.databinding.ActivityReviewBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_book_comment.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ReviewActivity : AppCompatActivity(), View.OnClickListener {


    private var mfirestore: FirebaseFirestore? = null
    var imm: InputMethodManager? = null //에딧 텍스트 키보드 내리는 역할
    private var title: String? = null
    private var author: String? = null
    private var isbn: String? = null
    private var name: String? = null
    private var email: String? = null
    private var img_url: String? = null
    private var formatDate: String? = null

    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding= ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mfirestore = FirebaseFirestore.getInstance()
        imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        //setContentView(R.layout.activity_review)

        binding.reviewName.text=name+"님"
        binding.reviewBtn.setOnClickListener(this)

    }


    override fun onStart() {
        super.onStart()

        mfirestore = FirebaseFirestore.getInstance()
        //회원정보 가져옴
        val review_info: ArrayList<BookInfo>
        review_info = ArrayList()

        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            getFacebookInfo(accessToken)
        }

        mfirestore?.collection("review")
            ?.whereEqualTo("book_num", isbn) //리사이클러뷰에 띄울 파이어베이스 테이블 경로
            ?.orderBy("date", Query.Direction.DESCENDING) //시간정렬순으로 이건 처음에 작성한게 제일 위로 올라감 게시글과 반대
            ?.addSnapshotListener { querySnapshot, error: FirebaseFirestoreException? ->
                review_info.clear() //미리 생성된 게시글들을 다시 불러오지않게 데이터를 한번 정리
                for (snapshot in querySnapshot!!.documents) {//여기 부분 해결해야함
                    var item = snapshot.toObject(BookInfo::class.java)//만들어둔 데이터 모델로 매핑됨
                    review_info.add(item!!)
                    // Log.d("확인","리뷰 확인:"+item.comment.toString())
                }
                comment_recycler.adapter= ReviewAdapter(this,review_info,email.toString(),1)

            }

    }

    private fun getFacebookInfo(accessToken: AccessToken) {//로그인된 정보 확인
        val graphrequest: GraphRequest =
            GraphRequest.newMeRequest(accessToken, object : GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(resultObject: JSONObject?, response: GraphResponse?) {
                    try {
                        name = resultObject?.getString("name");
                        email = resultObject?.getString("email");
                        img_url = resultObject?.getJSONObject("picture")?.getJSONObject("data")
                            ?.getString(
                                "url"
                            )
                        Log.d("확인", "여기는 book comment ->이름:${name} 이메일: ${email} image : ${img_url}")
                    } catch (e: JSONException) {
                        Log.d("확인", e.toString())
                    }
                }
            })
        val parameters: Bundle = Bundle()
        parameters.putString("fields", "id,name,email,picture.width(200)")
        graphrequest.parameters = parameters
        graphrequest.executeAsync()//새로운 스레드를 만들어서 처리함.

    }

    override fun onClick(v: View?) {
        var check: String? = binding.editReview.text.toString()
        if (check.equals("")) {
            Toast.makeText(this, "리뷰를 작성해주세요", Toast.LENGTH_SHORT).show()
        } else {
            var review_id = mfirestore?.collection("review")?.document()?.id
            getDate()//작성 시간 얻기

            var review_info = BookInfo()
            review_info.comment = binding.editReview.text.toString()
            review_info.name = name
            review_info.img = img_url
            review_info.book_num = isbn
            review_info.date = formatDate
            review_info.review_id = review_id
            review_info.user_email = email

            mfirestore?.collection("review")?.document(review_id!!)?.set(review_info)

            Toast.makeText(this, "메모가 저장되었습니다", Toast.LENGTH_SHORT).show()
            binding.editReview.setText("")
            imm?.hideSoftInputFromWindow(binding.editReview.windowToken, 0)
        }
    }
    fun getDate() {
        val now = System.currentTimeMillis()
        val mDate = Date(now)
        val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        formatDate = simpleDate.format(mDate)
    }
}