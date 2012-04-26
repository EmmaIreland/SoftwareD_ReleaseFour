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
        TrinketsTagLib.metaClass.g.link = {uri, body -> '<g:link uri= "' + uri.uri + '">' + body + '</g:link>'}
        TrinketsTagLib.metaClass.g.link = {controller, action, id, body -> ''}
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
    void testProtectedLink() {
        ttl.protectedLink([uri:'/person/show/3'], {'Body'})

        
        assertEquals out.toString(), '<g:link uri= "/person/show/3">Body</g:link>'
    }
}
