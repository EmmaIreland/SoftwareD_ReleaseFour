package survey

class TrinketsTagLib {
    static namespace = "trinkets"
    
    /**
    * Renders a div with (+) button and text
    *
    * @attr id
    * @attr name
    * @attr title
    * @attr onclick
    * @attr width REQUIRED width of div (css)
    */
   def addButton = { attrs, body ->
       def id = ifNullBlank( attrs['id'] )
       def name = ifNullBlank( attrs['name'] )
       def title = ifNullBlank( attrs['title'] )
       def onclick = ifNullBlank( attrs['onclick'] )
       
       def divTagStart = "<div class='addButton' id='${id}' name='${name}' onclick='${onclick}' " + \
           "title='${title}' style='width: ${attrs['width']}'>"
       def buttonImg = '<img alt="add icon" src="' + resource(dir:'images', file:'add.png') + '"//>'
       out << divTagStart << buttonImg << '<span>' << body() << '</span>' << '</div>'
   }
   
   /**
    * Renders an empty buttons bar (under dialog for consistent look)
    */
   def emptyButtonsBar = { attrs, body ->
       out << """<div class="buttons">
<span class="button"><input type="button" class="fakeButton"/></span>
</div>
"""
   }
   
   /**
    * Renders a fake button
    * (use case difference from emptyButtonsBar:
    * for when there may or may not be a single button)
    */
   def fakeButton = { attrs, body ->
       out << """<span class="button"><input type="button" class="fakeButton"/></span>"""
   }
   
   def makeHTMLNewlines = { attrs, body ->
       out << ifNullBlank(attrs['text']).toString().replace('\n', '<br/>')
   }
   
   /**
    * Renders a div that can be collapsed
    * 
    * @attr title REQUIRED : text that is show when collapsed
    * @attr collapsed : whether the div begins collapsed or not, default: true
    */
   def collapsibleDiv = { attrs, body ->
       def startCollapsed = !attrs['collapsed'] || attrs['collapsed'] == 'true'
       def state = (startCollapsed) ? 'collapsed' : 'expanded'
       
       def closingDivTag = '</div>'
       
       def headerTag = '<div class="header">'
       def header = headerTag + attrs['title'] + closingDivTag
       
       def contentTag = '<div class="content" />'
       def content = contentTag + body() + closingDivTag
       
       def collapsibleDivTag = '<div class="collapsibleDiv ' + state + '">'
       def collapsibleDiv = collapsibleDivTag + header + content + closingDivTag
       
       out << collapsibleDiv
   }
   
   /**
    * Renders a body as a link if link == 'true'
    * Can do uri or controller/action/id, prioritizes uri
    * If there is no uri and no valid link can be generated,
    * from controller/action/id
    * it will not render as a link
    * 
    * @attr link : defaults to true
    * @attr uri : example '/person/login'
    * @attr controller
    * @attr action
    * @attr id
    */
   def protectedLink = { attrs, body ->
       def link = attrs['link']
       if ( attrs['link'] == null ) {
           link = true
       }
       
       def uri = attrs['uri']
       def controller = attrs['controller']
       def action = attrs['action']
       def id = attrs['id']
       
       if ( link && ( uri || (controller && action) ) ) {
           if ( uri ) {
               out << g.link(uri: uri, body())
           } else {
               out << g.link(controller: controller,
                             action: action,
                             id: id,
                             body())
           }
       } else {
           out << body()
       }
   }
   
   def ifNullBlank(attribute) {
       (attribute) ? attribute : ''
   }
}
