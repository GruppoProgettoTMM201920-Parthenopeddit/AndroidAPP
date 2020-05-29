package it.uniparthenope.parthenopeddit.android.ui.chat

import androidx.lifecycle.MutableLiveData
import it.uniparthenope.parthenopeddit.model.Message

class ChatViewModel (){/*private val sendChatMessageUseCase: CompletableUseCase<SendMessageParams>,
                     private val openChatChannelUseCase: ObservableUseCase<String>){



    var messages= mutableListOf<Message>()
    var notifyNewMessageInsertedLiveData = MutableLiveData<Int>()

    fun openChatChannel() {
        openChatChannelUseCase.getObservable().subscribeBy(onNext = {
            messagesData.add(Message(it,Message.TYPE_FRIEND_MESSAGE))
            notifyNewMessageInsertedLiveData.value = messagesData.size

        }
        )
    }


    fun sendMessage(message: String) {
        messagesData.add(Message(message, Message.TYPE_MY_MESSAGE))
        notifyNewMessageInsertedLiveData.value = messagesData.size
        sendChatMessageUseCase.getCompletable(SendMessageParams(message)).subscribeBy(onComplete = {
        })
    }
    */

}