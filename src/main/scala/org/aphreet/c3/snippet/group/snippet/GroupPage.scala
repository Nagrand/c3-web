package org.aphreet.c3.snippet.group.snippet

import org.aphreet.c3.loc.ItemRewriteLoc
import org.aphreet.c3.model.Group
import net.liftweb.sitemap.Loc.{LinkText, Link}
import net.liftweb.common.{Logger, Full, Box}
import org.aphreet.c3.snippet.group.GroupPageData
import xml.Text
import net.liftweb.util.BindHelpers._

/**
 * Copyright iFunSoftware 2011
 * @author Dmitry Ivanov
 */
object GroupPage extends ItemRewriteLoc[Group, GroupPageData] {

  override val name = "Group"
  override def title = Text(currentValue.map(_.group.name.is).openOr("Group"))
  override def text = new LinkText[GroupPageData](text = v => Text(v.group.name.is))

  override val pathPrefix = "groups" :: Nil
  override lazy val pathList = pathPrefix ++ List("index")
  override def link = new Link[GroupPageData](pathList){
    override def pathList(value: GroupPageData): List[String] = pathPrefix ::: value.group.id.is.toString :: Nil
  }
  override def getItem(id: String) = Group.find(id)
  override def wrapItem(groupBox: Box[Group]) = groupBox.map(GroupPageData(_))
  override def canonicalUrl(data: GroupPageData) = {
    Full((pathPrefix:::List(data.group.id.is.toString)).mkString("/","/",""))
  }
}

class GroupPage(data: GroupPageData) extends GroupPageHelpers{
  override lazy val group = data.group
  override lazy val activeLocId = "about"

  private val logger = Logger(classOf[GroupPage])

}

trait GroupPageHelpers {
  val group: Group
  val activeLocId: String
  def embedTabMenu = {
    "* *" #> <lift:embed what="/groups/_group_tab_menu" active={activeLocId} group_id={group.id.is.toString} />
  }
}

