package data.provider;

import data.FacebookData;

/**
 * FileDataProvider which builds {@link data.FacebookData} for each String.
 */
public class FaceBookDataProvider extends FileDataProvider<FacebookData> {


    public FaceBookDataProvider(String mSourceFile) {
        super(mSourceFile);
    }

    @Override
    public FacebookData dataFromString(String aStringToken) {
        //TODO:Although the description ensures well formatted input this is not the best approach to extract the data.
        final String[] splittedContent = aStringToken.split("   ");
        String date = "";
        for (int i = 0; i < splittedContent.length - 2; i++) {
            date += " " + splittedContent[i];
        }
        final String target = splittedContent[splittedContent.length - 1];
        final String source = splittedContent[splittedContent.length - 2];
        return new FacebookDataImpl(date.trim(), source.trim(), target.trim());
    }
}
