package com.timetonic.booklistapp.data.remote.model

import com.timetonic.booklistapp.BuildConfig
import com.timetonic.booklistapp.data.local.model.BookUi

data class AllBooksResponse(
    val status: String,
    val errorCode: String?,
    val errorMsg: String?,
    val sstamp: Long?,
    val allBooks: AllBooks?,
)

data class AllBooks(
    val nbBooks: Int, val nbContacts: Int, val contacts: List<Contact>, val books: List<Book>
)

data class Contact(
    val u_c: String,
    val lastName: String,
    val firstName: String,
    val sstamp: Long,
    val isConfirmed: Boolean
)

data class Book(
    val accepted: Boolean,
    val archived: Boolean,
    val sstamp: Long,
    val del: Boolean,
    val b_c: String,
    val b_o: String,
    val contact_u_c: String?,
    val nbNotRead: Int,
    val nbMembers: Int,
    val members: List<Member>,
    val fpForm: FpForm,
    val lastMsg: LastMsg,
    val userPrefs: UserPrefs,
    val ownerPrefs: OwnerPrefs,
    val sbid: Int,
    val lastMsgRead: Int,
    val lastMedia: Int,
    val favorite: Boolean,
    val order: Int
)

fun Book.toBookUi() = BookUi(
    title = this.ownerPrefs.title,
    imageUrl = if (this.ownerPrefs.oCoverImg == null) null
    else BuildConfig.API_BASE_URL + this.ownerPrefs.oCoverImg
)

data class Member(
    val u_c: String, val invite: String, val right: Int
)

data class FpForm(
    val sfpid: Int, val name: String, val lastModified: Long
)

data class LastMsg(
    val smid: Int,
    val uuid: String,
    val sstamp: Long,
    val lastCommentId: Int,
    val msgBody: String,
    val msgBodyHtml: String,
    val msgType: String,
    val msgMethod: String,
    val msgColor: String,
    val nbComments: Int,
    val pid: Int,
    val nbMedias: Int,
    val nbEmailCids: Int,
    val nbDocs: Int,
    val b_c: String,
    val b_o: String,
    val u_c: String,
    val msg: String,
    val del: Boolean,
    val created: Long,
    val lastModified: Long,
    val medias: List<Media>
)

data class Media(
    val id: Int,
    val ext: String,
    val originName: String,
    val internName: String,
    val size: Int,
    val type: String,
    val emailCid: String,
    val del: Boolean
)

data class UserPrefs(
    val maxMsgsOffline: Int,
    val syncWithHubic: Boolean,
    val uCoverLetOwnerDecide: Boolean,
    val uCoverColor: String,
    val uCoverUseLastImg: Boolean,
    val uCoverImg: String?,
    val uCoverType: String,
    val inGlobalSearch: Boolean,
    val inGlobalTasks: Boolean,
    val notifyEmailCopy: Boolean,
    val notifySmsCopy: Boolean,
    val notifyMobile: Boolean,
    val notifyWhenMsgInArchivedBook: Boolean
)

data class OwnerPrefs(
    val oCoverColor: String,
    val oCoverUseLastImg: Boolean,
    val oCoverImg: String?,
    val oCoverType: String,
    val authorizeMemberBroadcast: Boolean,
    val acceptExternalMsg: Boolean,
    val title: String,
    val notifyMobileConfidential: Boolean
)