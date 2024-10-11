package ar.lamansys.messages;

import ar.lamansys.messages.application.AddUser;
import ar.lamansys.messages.application.AssertUserExists;
import ar.lamansys.messages.application.AssertUserNotExists;
import ar.lamansys.messages.application.messages.ClearData;
import ar.lamansys.messages.application.messages.FetchUserChat;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.application.messages.ListContacts;
import ar.lamansys.messages.application.messages.SendUserMessage;
import ar.lamansys.messages.application.messages.SetUserSession;
import ar.lamansys.messages.infrastructure.output.impl.MessageStorageListImpl;
import ar.lamansys.messages.infrastructure.output.impl.messages.UserSessionStorageImpl;
import ar.lamansys.messages.infrastructure.output.impl.messages.UserStorageSetImpl;

public class MessageAppConfiguration {


    public static TestMessageApp newBean() {
        var messageStorage = new MessageStorageListImpl();
        var userSessionStorage = new UserSessionStorageImpl();
        var userStorage =new UserStorageSetImpl();

        var assertUserExists = new AssertUserExists(userStorage);
        var assertUserNotExists = new AssertUserNotExists(userStorage);

        AddUser addUser = new AddUser(userStorage, assertUserNotExists);
        GetUserSession getUserSession = new GetUserSession(userSessionStorage);
        ClearData clearData = new ClearData(messageStorage, userStorage, userSessionStorage);
        FetchUserChat fetchUserChat = new FetchUserChat(getUserSession, messageStorage, assertUserExists);
        ListContacts listContacts = new ListContacts(getUserSession, messageStorage);
        SendUserMessage sendUserMessage = new SendUserMessage(getUserSession, messageStorage, assertUserExists);
        SetUserSession setUserSession = new SetUserSession(userSessionStorage, assertUserExists);

        return new TestMessageApp(
                addUser,
                clearData,
                fetchUserChat,
                getUserSession,
                listContacts,
                sendUserMessage,
                setUserSession
        );
    }
}

