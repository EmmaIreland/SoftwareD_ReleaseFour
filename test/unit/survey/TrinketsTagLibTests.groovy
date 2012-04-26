package survey

import grails.test.*

class TrinketsTagLibTests extends TagLibUnitTestCase {
    StringWriter out
    TrinketsTagLib ttl
    def collapsedDivPattern = /<div.*collapsed.*><div.*>.*<\/div><div.*\/><\/div><\/div>/
    def expandedDivPattern = /<div.*expanded.*><div.*>.*<\/div><div.*\/><\/div><\/div>/

    protected void setUp() {
        super.setUp()
        out = new StringWriter()
        TrinketsTagLib.metaClass.out = out
        ttl = new TrinketsTagLib()
    }

    protected void tearDown() {
        super.tearDown()
        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove TrinketsTagLib
    }

    void testCollapsibleDivExpanded() {
        ttl.collapsibleDiv([title:'title', collapsed:'false'], {""})
        assertTrue out.toString().matches(expandedDivPattern)
    }

    void testCollapsibleDivCollapsed() {
        ttl.collapsibleDiv([title:'title', collapsed:'true'], {""})
        assertTrue out.toString().matches(collapsedDivPattern)
    }

    void testCollapsibleDivDefault() {
        ttl.collapsibleDiv([title:'title'], {""})
        assertTrue out.toString().matches(collapsedDivPattern)
    }

    void testIfNullBlank() {
        def results = ttl.ifNullBlank('thing')
        assertEquals results, 'thing'
        results = ttl.ifNullBlank(null)
        assertEquals results, ''
    }

    void testEmptyButtonsBar() {
        def results = ttl.emptyButtonsBar([:], {""})
        assertEquals results, ttl.out
    }
    void testProtectedLinkCaseOne() {
        TrinketsTagLib.metaClass.g.link = {attrs, body -> '<g:link uri= "' + attrs.uri + '">' + body + '</g:link>'}
        ttl.protectedLink([uri:'/person/show/3'], {'Body'})
        assertEquals out.toString(), '<g:link uri= "/person/show/3">Body</g:link>'
    }
    void testProtectedLinkCaseTwo(){
        TrinketsTagLib.metaClass.g.link = {attrs, body -> '<g:link controller= "' + attrs.controller + '" action= "' + attrs.action + '" id= ' + attrs.id +'>' + body + '</g:link>'}
        ttl.protectedLink([controller:'controller', action:'action', id:10], {'Body'})
        assertEquals out.toString(), '<g:link controller= "controller" action= "action" id= 10>Body</g:link>'
    }
    void testProtectedLinkCaseThree(){
        TrinketsTagLib.metaClass.g.link = {body -> body}
        ttl.protectedLink([:], {'Body'})
        assertEquals out.toString(), 'Body'
    }
}
