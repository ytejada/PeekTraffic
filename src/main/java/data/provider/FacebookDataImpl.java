package data.provider;

import data.FacebookData;

/**
 * Representation of the input data defined by the file used for PeekTraffic problem from Facebook. Each instance provides
 * data about the date when the communication took pace, and the source and destination users involved in the communication.
 */
public class FacebookDataImpl implements FacebookData {

    private String mCommunicationDate;
    private String sourceUser;
    private String targetUser;


    FacebookDataImpl(String mCommunicationDate, String sourceUser, String targetUser) {
        this.mCommunicationDate = mCommunicationDate;
        this.sourceUser = sourceUser;
        this.targetUser = targetUser;
    }

    @Override
    public String getCommunicationDate() {
        return mCommunicationDate;
    }

    @Override
    public String getSourceUser() {
        return sourceUser;
    }

    @Override
    public String getTargetUser() {
        return targetUser;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacebookDataImpl that = (FacebookDataImpl) o;

        if (mCommunicationDate != null ? !mCommunicationDate.equals(that.mCommunicationDate) : that.mCommunicationDate != null)
            return false;
        if (sourceUser != null ? !sourceUser.equals(that.sourceUser) : that.sourceUser != null) return false;
        if (targetUser != null ? !targetUser.equals(that.targetUser) : that.targetUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mCommunicationDate != null ? mCommunicationDate.hashCode() : 0;
        result = 31 * result + (sourceUser != null ? sourceUser.hashCode() : 0);
        result = 31 * result + (targetUser != null ? targetUser.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FacebookData{" +
                "mCommunicationDate='" + mCommunicationDate + '\'' +
                ", sourceUser='" + sourceUser + '\'' +
                ", targetUser='" + targetUser + '\'' +
                '}';
    }
}
