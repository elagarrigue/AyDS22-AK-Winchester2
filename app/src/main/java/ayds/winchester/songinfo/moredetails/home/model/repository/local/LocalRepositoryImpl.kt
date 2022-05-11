package ayds.winchester.songinfo.moredetails.home.model.repository.local

import android.content.Context
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.model.repository.local.sqldb.DataBase
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

interface LocalRepository {
    fun updateArtistTerm(id : String)
    fun getArtistDescription(artistName:String): Description?
    fun getArtistDescriptionById(id:String): Description?
    fun saveDescriptionInDataBase(artistDescription: Description)
}
internal class LocalRepositoryImpl(private var otherInfoView : OtherInfoWindow) : LocalRepository{
    private lateinit var dataBase: DataBase
    private lateinit var artistName: String

    init{
        initProperties()
    }

    private fun initProperties(){
        dataBase = DataBase(otherInfoView as Context)
    }

    override fun updateArtistTerm(id : String){
        dataBase.updateArtistTerm(artistName, id)
    }

     override fun getArtistDescription(artistName:String): Description?{
         this.artistName = artistName
        val artistDescription = dataBase.getInfo(dataBase, artistName)

         return artistDescription

    }

    override fun getArtistDescriptionById(id:String): Description?{
        val artistDescription = dataBase.getInfoById(dataBase, id)

        return artistDescription

    }

    override fun saveDescriptionInDataBase(artistDescription: Description){
        dataBase.saveArtist(artistName, artistDescription.description,artistDescription.id)
    }
}