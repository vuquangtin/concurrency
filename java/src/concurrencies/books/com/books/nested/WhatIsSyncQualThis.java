package com.books.nested;
public class WhatIsSyncQualThis {
	 
	class IJustDriftedAway {
		void superFoo() {
			
			synchronized(IJustDriftedAway.this) {
				System.out.println("1");
				if( Thread.holdsLock(WhatIsSyncQualThis.this) )
					System.out.println("1 - held");
			}
			
			synchronized(this) {
				System.out.println("2");
				if( Thread.holdsLock(WhatIsSyncQualThis.this) )
					System.out.println("2 - held");
			}
			
			synchronized(WhatIsSyncQualThis.this) {
				System.out.println("3");
				if( Thread.holdsLock(this) )
					System.out.println("3 - held");
			}
			
			synchronized(this) {
				System.out.println("4");
				if( Thread.holdsLock(this) )
					System.out.println("4 - held");
			}
		}
	}
	
	void fooIt() {
		(new IJustDriftedAway()).superFoo();
	}
	
	public static void main(String[] args) {
		System.out.println("Running");
		(new WhatIsSyncQualThis()).fooIt();
		System.out.println("Done");
	}
}