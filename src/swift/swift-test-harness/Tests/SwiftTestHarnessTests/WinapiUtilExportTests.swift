import Testing
import WinapiUtil

@Suite("WinapiUtil Swift Export Tests")
struct WinapiUtilExportTests {
    @Test func testSwiftModuleLoads() throws {
        #expect(Bool(true), "WinapiUtil swift module imported cleanly")
    }

    @Test func testIsHiddenAttribute() throws {
        #expect(isHidden(fileAttributes: FILE_ATTRIBUTE_HIDDEN))
        #expect(isHidden(fileAttributes: 0x82))
        #expect(!isHidden(fileAttributes: 0))
        #expect(!isHidden(fileAttributes: 1))
    }

    @Test func testComputerNameKindFormats() throws {
        #expect(ComputerNameKind.allCases.count == 8)
        #expect(ComputerNameKind.NetBios.toFormat() == 0)
        #expect(ComputerNameKind.DnsHostname.toFormat() == 1)
        #expect(ComputerNameKind.DnsDomain.toFormat() == 2)
        #expect(ComputerNameKind.DnsFullyQualified.toFormat() == 3)
        #expect(ComputerNameKind.PhysicalNetBios.toFormat() == 4)
        #expect(ComputerNameKind.PhysicalDnsHostname.toFormat() == 5)
        #expect(ComputerNameKind.PhysicalDnsDomain.toFormat() == 6)
        #expect(ComputerNameKind.PhysicalDnsFullyQualified.toFormat() == 7)
    }

    @Test func testHandleAndHandleRefRawRoundTrip() throws {
        let owned = Handle.Companion.shared.fromRawHandle(handle: 42)
        #expect(owned.asRawHandle() == 42)
        #expect(owned.asRaw() == 42)

        let borrowedFromOwned = owned.asHandleRef()
        #expect(borrowedFromOwned.asRawHandle() == 42)
        #expect(borrowedFromOwned.asRaw() == 42)

        let extracted = owned.intoRawHandle()
        #expect(extracted == 42)
        owned.drop()

        let borrowed = HandleRef.Companion.shared.fromRawHandle(handle: 99)
        let cloned = borrowed.clone()
        #expect(borrowed.asRawHandle() == 99)
        #expect(cloned.asRawHandle() == 99)
        #expect(cloned.asRaw() == 99)
    }

    @Test func testSmallRectAndConsoleEnums() throws {
        let rect = SmallRect(left: 1, top: 2, right: 80, bottom: 25)
        #expect(rect.left == 1)
        #expect(rect.top == 2)
        #expect(rect.right == 80)
        #expect(rect.bottom == 25)

        let resized = rect.copy(left: 1, top: 2, right: 120, bottom: 40)
        #expect(resized.right == 120)
        #expect(resized.bottom == 40)
        #expect(rect != resized)

        #expect(Color.allCases.count == 8)
        #expect(Color.Black != Color.White)
        #expect(Color.Cyan != Color.Red)
        #expect(Intense.Yes != Intense.No)
    }
}
