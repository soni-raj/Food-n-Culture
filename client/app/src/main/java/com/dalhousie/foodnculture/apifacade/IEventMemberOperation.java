package com.dalhousie.foodnculture.apifacade;

import java.util.List;

import com.dalhousie.foodnculture.models.EventMember;

public interface IEventMemberOperation extends ICrudOperation <EventMember, Integer> {
    public List<EventMember> getMembersByEventId(Integer eventId);
}