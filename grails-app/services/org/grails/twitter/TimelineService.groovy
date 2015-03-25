package org.grails.twitter

import org.grails.twitter.auth.Person

class TimelineService {

    void clearTimelineCacheForUser(String username) {}

    def getTimelineForUser(String username) {
        def per = Person.where {
            userName == username
        }.find()

        def query = Status.whereAny {
            author {
                userName == per.userName
            }
            if(per.followed) author in per.followed
        }.order 'dateCreated', 'desc'

        def messages = query.list(max: 10)

        messages
    }
}
