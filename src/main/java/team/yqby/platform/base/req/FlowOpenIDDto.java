package team.yqby.platform.base.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class FlowOpenIDDto implements Serializable {

    private static final long serialVersionUID = 7941220567026640733L;

    private String openID;

}
