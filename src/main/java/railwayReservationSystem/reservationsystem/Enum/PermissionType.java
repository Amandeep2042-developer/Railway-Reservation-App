package reservationsystem.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PermissionType {
        TRAIN_CREATE("train :create"),
        TRAIN_UPDATE("train :update"),
        TRAIN_DELETE("train :delete"),
        TRAIN_READ("train :read"),

        USER_READ("user :read"),
        USER_DELETE("user :delete"),

        BOOK_TICKET("book :ticket"),
        CANCEL_TICKET("cancel :ticket");

    private final String permission;
    }

