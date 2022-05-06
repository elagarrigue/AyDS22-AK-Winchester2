package ayds.winchester.songinfo.moredetails.home.model.repository.local

import android.content.Context
import ayds.winchester.songinfo.moredetails.home.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

private const val PREFIX = "[*]"

class LocalRepository(otherInfoView : OtherInfoWindow) {

    private lateinit var dataBase: DataBase
    private var otherInfoView : OtherInfoWindow = otherInfoView
    private lateinit var artistName: String

    init{
        initProperties()
    }

    private fun initProperties(){
        dataBase = DataBase(otherInfoView as Context)
    }

     fun getArtistDescription(artistName:String): Description?{
         this.artistName = artistName
        var artistDescription = dataBase.getInfo(dataBase, artistName)
        if (artistDescription != null) {
            artistDescription = PREFIX + "$artistDescription"
            return ArtistDescription("123", artistDescription!!)
        }
         return null;
    }

    fun getArtistDescriptionById(id:String): Description?{
        var artistDescription = dataBase.getInfoById(dataBase, id)
        if (artistDescription != null)
            artistDescription = PREFIX +"$artistDescription"

        return ArtistDescription(id, artistDescription!!)
    }

    fun saveDescriptionInDataBase(artistDescription: String){
        dataBase.saveArtist(artistName, artistDescription)
    }
}