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

    fun updateArtistTerm(id : String){
        dataBase.updateArtistTerm(artistName, id)
    }

     fun getArtistDescription(artistName:String): Description?{
         this.artistName = artistName
        val artistDescription = dataBase.getInfo(dataBase, artistName)
        if (artistDescription != null) {
            artistDescription.description = PREFIX + artistDescription.description
            return artistDescription
        }
         return null
    }

    fun getArtistDescriptionById(id:String): Description?{
        var artistDescription = dataBase.getInfoById(dataBase, id)
        if (artistDescription != null) {
            artistDescription = PREFIX + "$artistDescription"
            return ArtistDescription(id, artistDescription)
        }else
            return null
    }

    fun saveDescriptionInDataBase(artistDescription: Description){
        dataBase.saveArtist(artistName, artistDescription.description,artistDescription.id)
    }
}